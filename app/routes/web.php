<?php

Route::get('/', 'IndexController@index')->name('index');

Route::post('/{type}', 'IndexController@execute')->name('execute');
