FROM node:latest as builder
WORKDIR /app
COPY package.json .
COPY package-lock.json .
COPY . .
RUN npm install
RUN npm run build
EXPOSE 3000
CMD ["npm", "run", "dev"]
