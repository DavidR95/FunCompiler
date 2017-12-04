<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class IndexController extends Controller
{
    /**
     * Display the index page with no code display.
     *
     * @return \Illuminate\View\View
     */
    public function index()
    {
        return view('index');
    }

    /**
     * Makes an API request to the Fun compiler and retrieves the JSON response.
     *
     * @return \Illuminate\View\View
     */
    public function execute()
    {
        // An empty program cannot be submitted
        request()->validate([
            'program' => 'required'
        ]);
        // Create a new Guzzle client
        $client = new \GuzzleHttp\Client();
        // Send a post request to the compiler container
        $res = $client->request('POST', 'http://compiler:4567', [
            'form_params' => [
                // Pass the input program as a parameter
                'program' => request()->program
            ]
        ]);
        // Convert the body of the response to an associative array
        $response = json_decode($res->getBody(), true);
        // Render the index view, passing along the array
        return response()->json(['redirect_url' => '/', 'response' => $response]);
    }
}
