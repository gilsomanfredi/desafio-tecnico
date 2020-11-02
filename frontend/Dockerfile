FROM nginx:stable-alpine

RUN rm -rf /usr/share/nginx/html/*

COPY dist /usr/share/nginx/html

RUN rm /etc/nginx/conf.d/default.conf
RUN rm /etc/nginx/nginx.conf

COPY nginx/default.conf /etc/nginx/conf.d/
COPY nginx/nginx.conf /etc/nginx/

EXPOSE 80
