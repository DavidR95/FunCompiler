<?php

Route::get('/', 'IndexController@index')->name('index');

Route::post('/', 'IndexController@execute')->name('execute');
