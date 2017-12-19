<?php

namespace Tests\Feature;

use Tests\TestCase;

class HttpTest extends TestCase
{
    /**
     * Make a post request for a contextual analysis result and ensure only
     * the correct fragments are returned.
     *
     * @return void
     */
    public function testSuccessfulCARequest()
    {
        $response = $this->json('POST', route('execute', [
            'type' => 'ca',
            'program' => 'int n = 15 proc main(): while n > 1: n = n/2 . .'
        ]));

        $response->assertSuccessful()
                 ->assertJsonFragment([
                     'redirect_url' => '/',
                     'numSyntaxErrors' => 0,
                     'numContextualErrors' => 0,
                     'syntaxErrors' => [],
                     'contextualErrors' => []
                 ])
                 ->assertSeeText('nodeOrder')
                 ->assertSeeText('treeNodes')
                 ->assertDontSeeText('objectCode');
    }

    /**
     * Make a post request for a contextual analysis result and ensure only
     * the correct fragments are returned.
     *
     * @return void
     */
    public function testSuccessfulCGRequest()
    {
        $response = $this->json('POST', route('execute', [
            'type' => 'cg',
            'program' => 'int n = 15 proc main(): while n > 1: n = n/2 . .'
        ]));

        $response->assertSuccessful()
                 ->assertJsonFragment([
                     'redirect_url' => '/',
                     'numSyntaxErrors' => 0,
                     'numContextualErrors' => 0,
                     'syntaxErrors' => [],
                     'contextualErrors' => []
                 ])
                 ->assertSeeText('nodeOrder')
                 ->assertSeeText('treeNodes')
                 ->assertSeeText('objectCode');
    }
}
