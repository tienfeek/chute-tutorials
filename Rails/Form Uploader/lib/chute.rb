require 'httparty'
require 'json'

class Chute
  include HTTParty

  base_uri 'http://api.getchute.com'
  format :json

  # Public: Initializer for the Chute class
  #
  # app_id        - The String representing your application's app_id
  # access_token  - The String representing a valid access token
  #
  # Returns the Chute class
  def initialize(app_id, access_token)
    @app_id         = app_id
    @authorization  = access_token
  end


  # Public: Uploads photos to Chute
  #
  # file_paths    - String for a single file path
  #                 Object of an uploaded file
  #                 Array of either of the above  
  # chutes        - Integer for a single chute
  #                 Array of Chute IDs
  #
  # Examples
  # 
  #   upload('/path/to/file', 1)
  #   # =>  { :parcel=>100, :paths=>["http://media.getchute.com/media/Opega"] }
  #
  #   upload(['/path/to/file1','/path/to/file1'], 1)
  #   # =>  { :parcel=>100, :paths=>["http://media.getchute.com/media/Opega","http://media.getchute.com/media/Jpega"] }  
  #
  # Returns A Hash containing the Parcel ID and paths to the uploaded files
  def upload(file_paths, chutes)
    file_paths = [file_paths] unless Array === file_paths
    chutes     = [chutes]     unless Array === chutes

    # Loop over the file inputs
    file_paths = file_paths.collect do |file_path|
      # If this is a form upload
      if file_path.respond_to?(:tempfile)
        _details ={
          :filename => file_path.tempfile.path,
          :md5      => file_path.tempfile.size,
          :size     => file_path.tempfile.size
        }
        _details
      
      # If this is a path to a file
      else
        File.open(file_path, 'r') do |f|
          _details = {
            :filename => file_path,
            :md5      => f.size,
            :size     => f.size
          }
        end
        _details
      end
    end

    # Create the parcel with the full set of photos
    response = create_parcel(file_paths, chutes)
    
    # Collect Paths and Generate Upload Tokens
    paths = response.parsed_response['uploads'].collect do |upload|
      generate_token(upload)
    end

    # Prepare data to return
    data = {
      :parcel => response.parsed_response['id'],
      :paths  => paths
    }

    data
  end

  private
    # Private: Prepares HTTP headers
    #
    # Returns a Hash of HTTP headers
    def headers
      {
        'x-client_id'   => @app_id,
        'Authorization' => 'OAUTH ' + @authorization
      }
    end
  
  
    # Private: Creates a Parcel in Chute
    #
    # file_paths    - Array of files  
    # chutes        - Array of Chute IDs
    #
    # Returns a HTTP Response Object
    def create_parcel(file_paths, chutes)
      self.class.post('/v1/parcels', { :headers=> headers, :body=> {
        :files  => JSON.unparse(file_paths),
        :chutes => JSON.unparse(chutes)
      } })
    end


    # Private: Generates an Upload Token for a specific Asset
    #
    # upload    - String represeting the local file path  
    #
    # Returns a String representing the URL to upload the file to
    def generate_token(upload)
      response   = self.class.get("/v1/uploads/#{upload['asset_id']}/token", { :headers=> headers })
      s3_options = response.parsed_response
      upload_to_s3(upload, s3_options)
      response.parsed_response['media_url']
    end


    # Private: Uploads the file to Amazon S3
    #
    # upload      - String represeting the local file path
    # s3_options  - Hash containing:
    #               :date - Date upload token is valid until
    #               :signature - String with AWS upload token
    #               :content_type - String with the file type
    #
    # Returns a String representing the URL to upload the file to
    def upload_to_s3(upload, s3_options)
      headers = {
        'Date'          => s3_options['date'],
        'Authorization' => s3_options['signature'],
        'Content-Type'  => s3_options['content_type'],
        'x-amz-acl'     => 'public-read'
      }

      file = File.open(upload['file_path'], 'r')
      response = self.class.put( s3_options['upload_url'], :body => file.read, :headers => headers )
      file.close

      complete(upload)
    end


    # Private: Mark asset as completed (forces processing)
    #
    # upload      - String represeting the local file path
    #
    # Returns nothing
    def complete(upload)
      self.class.post("/v1/uploads/#{upload['asset_id']}/complete", { :headers=> headers })
    end
end