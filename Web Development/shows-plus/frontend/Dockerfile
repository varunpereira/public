FROM node:14
WORKDIR /app-name
COPY package.json .
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]

# create image: docker build -t <username:app name> .
# create container & run it: docker run -p <port wanted locally:port exposed in docker> <username:app name>