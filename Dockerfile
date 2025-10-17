# Stage 1: Build Angular app
FROM node:18 AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build --prod

# Stage 2: Serve using NGINX
FROM nginx:alpine
COPY --from=build /app/dist/ashokit_ecomm_store /usr/share/nginx/html
EXPOSE 80
