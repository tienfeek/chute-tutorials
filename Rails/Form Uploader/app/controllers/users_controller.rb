require 'chute'
require 'ostruct'

CHUTE = OpenStruct.new({
                      app_id:       'INSERT_APP_ID_HERE',
                      access_token: 'INSERT_ACCESS_TOKEN_HERE'
                    })

class UsersController < ApplicationController
  def index
    @users = User.all
  end

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
    response = Chute.new(CHUTE.app_id, CHUTE.access_token).upload(params[:user][:avatar_url], 1)
    user = User.create({
                         name: params[:user][:name],
                         avatar_url: response[:paths].first
                       })
    redirect_to :action => :show, :id => user.id
  end

  def show
    @user = User.find(params[:id])
  end

  def edit
    @user = User.find(params[:id])
  end

  def update
    user = User.find(params[:id])
    user.update_attributes(params[:user])
    redirect_to :action => :show, :id => params[:id]
  end

  def destroy
    User.find(params[:id]).destroy
    redirect_to :action => :index
  end
end
