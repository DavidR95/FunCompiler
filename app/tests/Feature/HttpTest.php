<?php

namespace Tests\Feature;

use Tests\TestCase;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Foundation\Testing\WithoutMiddleware;

class HttpTest extends TestCase
{
    /**
     * A basic test example.
     *
     * @return void
     */
    public function testExecuteRequest()
    {
        $response = $this->json('POST', route('execute', [
            'type' => 'ca',
            'program' => 'int n = 15 proc main(): while n > 1: n = n/2 . .'
        ]));

        $response->assertStatus(200);
    }

}
