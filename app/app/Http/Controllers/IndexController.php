<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class IndexController extends Controller
{
    /**
     * Display the home page with no currently executed program, i.e., display
     * the Fun specification.
     *
     * @return \Illuminate\View\View
     */
    public function index()
    {
        return view('index');
    }

    /**
     * Makes an API request to the Fun compiler, passing the input program and
     * the compilation type (contextual analysis or code generation) as
     * parameters. Retrieves the JSON response and returns this along with
     * a redirect URL (the home page) to the AJAX request that initially
     * triggered this method.
     *
     * @return \Illuminate\Http\JsonResponse
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
                'program' => request()->program,
                // Pass the execution type as a parameter
                'type' => request()->type
            ]
        ]);
        // Convert the body of the response to an associative array
        $response = json_decode($res->getBody(), true);
        // Render the index view, passing along the response array
        return response()->json(['redirect_url' => '/',
                                 'response' => $response]);
    }
}
