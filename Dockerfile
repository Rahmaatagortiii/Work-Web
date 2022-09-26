# Stage 1: Compile and Build angular codebase

# Use official node image as the base image (from dockerHub we must get a lts nodeJs Version ) 
# mouch lazem tkoun nafs l version mte3kom it doesnt matter
FROM node:latest as build

# Set the working directory
WORKDIR /usr/local/app

# Add the source code to app
COPY ./ /usr/local/app/

# Install all the dependencies
RUN npm install

# Generate the build of the application
RUN npm run build

# in this stage we can say 3ak3ak 7alla m3ak
# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
FROM nginx:latest

# Copy the build output to replace the default nginx contents.
COPY --from=build /usr/local/app/dist/frontend /usr/share/nginx/html

# Expose port 80(we can change this port base of what where we are going to deploy our front application)
EXPOSE 80