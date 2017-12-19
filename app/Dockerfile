# Use a PHP7.0 image that is packaged with the Apache web server
FROM php:7.0-apache
# Download composer and install composer.phar in /usr/local/bin
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer
# Create the 'app' directory and set to be the working directory of the container
WORKDIR /app
# Copy all files in current directory to the working directory of the container
COPY . /app
# Update packages and install composer and PHP dependencies.
RUN apt-get update -yqq
RUN apt-get install git zlib1g-dev -yqq
RUN pecl install xdebug
# Compile PHP, include these extensions.
RUN docker-php-ext-install zip
RUN docker-php-ext-enable xdebug
# Use the 'composer.json' file to download any PHP dependencies (including Laravel)
RUN composer install
