FROM php:8.2-apache

RUN docker-php-ext-install mysqli && docker-php-ext-enable mysqli

COPY *.php style.css /var/www/html/

COPY init.sql /docker-entrypoint-initdb.d/init.sql