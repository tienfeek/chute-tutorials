require 'chute'

CHUTE = {
  app_id:       'INSERT_APP_ID_HERE',
  access_token: 'INSERT_ACCESS_TOKEN_HERE'  
}

class UsersController < ApplicationController
  # GET /users/new
  # GET /users/new.json
  def new
    @user = User.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @user }
    end
  end

  # POST /users
  # POST /users.json
  def create
    render :text=> Chute.new(CHUTE.app_id, CHUTE.access_token).upload(params[:user][:avatar_url], 1)
  end
end
