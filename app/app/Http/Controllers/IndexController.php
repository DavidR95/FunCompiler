<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class IndexController extends Controller
{
    public function index()
    {
        return view('index', ['body' => null]);
    }

    public function execute()
    {
        $client = new \GuzzleHttp\Client();
        $res = $client->request('POST', 'http://compiler:4567', [
            'form_params' => [
                'program' => request()->program
            ]
        ]);
        $body = json_decode($res->getBody(), true);
        return view('index', ['body' => $body]);
    }
}
