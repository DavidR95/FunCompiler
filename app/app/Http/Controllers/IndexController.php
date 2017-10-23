<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class IndexController extends Controller
{
    public function index()
    {
        $client = new \GuzzleHttp\Client();
        $res = $client->request('POST', 'http://compiler:4567', [
            'form_params' => [
                'code' => request()->code
            ]
        ]);
        $body = json_decode($res->getBody(), true);
        return view('index', ['body' => $body]);
    }
}
