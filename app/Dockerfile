# Use a PHP7.0 image that is packaged with the Apache web server
FROM php:7.0-apache
# Download composer and install composer.phar in /usr/local/bin
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer
# Create the 'app' directory and set to be the working directory of the container
WORKDIR /app
# Copy all files in current directory to the working directory of the container
COPY . /app
# Use the 'composer.json' file to download any PHP dependencies (including Laravel)
RUN composer install
