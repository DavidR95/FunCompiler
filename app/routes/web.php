<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    $client = new \GuzzleHttp\Client();
    $res = $client->request('POST', 'http://compiler:4567');
    $body = json_decode($res->getBody(), true);
    return view('welcome', ['body' => $body]);
});
