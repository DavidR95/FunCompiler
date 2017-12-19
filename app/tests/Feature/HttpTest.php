<?php

namespace Tests\Feature;

use Tests\TestCase;

class HttpTest extends TestCase
{
    /**
     * Ensure the 'index' view is rendered when navigating to '/'.
     *
     * @return void
     */
    public function testIndexView() {
        $response = $this->get('/');
        $response->assertViewIs('index');
    }

    /**
     * Make a post request for a contextual analysis result with a correct
     * program and ensure only the correct fragments are returned.
     *
     * @return void
     */
    public function testSuccessfulAndValidCARequest()
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
    * Make a post request for a contextual analysis result with an incorrect
    * program and ensure only the correct fragments are returned.
     *
     * @return void
     */
    public function testSuccessfulAndInvalidCARequest()
    {
        $response = $this->json('POST', route('execute', [
            'type' => 'ca',
            'program' => 'int n = 15 proc main(): while n > 1 n = n/2 . .'
        ]));

        $response->assertSuccessful()
                 ->assertJsonFragment([
                     'redirect_url' => '/',
                     'numSyntaxErrors' => 1,
                     'numContextualErrors' => 0,
                     'syntaxErrors' => ['line 1:36 missing \':\' at \'n\''],
                 ])
                 ->assertDontSeeText('nodeOrder')
                 ->assertDontSeeText('treeNodes');
    }

    /**
     * Make a post request for a contextual analysis result with an empty
     * and ensure a 422 'Unprocessable Entity' status code is returned.
     *
     * @return void
     */
    public function testUnsuccessfulCARequest()
    {
        $response = $this->json('POST', route('execute', [
            'type' => 'ca',
            'program' => ''
        ]));

        $response->assertStatus(422)
                 ->assertSeeText('The program field is required.');
    }

    /**
     * Make a post request for a code generation result and ensure only
     * the correct fragments are returned.
     *
     * @return void
     */
    public function testSuccessfulAndValidCGRequest()
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

    /**
    * Make a post request for a code generation result with an incorrect
    * program and ensure only the correct fragments are returned.
     *
     * @return void
     */
    public function testSuccessfulAndInvalidCGRequest()
    {
        $response = $this->json('POST', route('execute', [
            'type' => 'cg',
            'program' => 'int n = 15 proc main(): while n > 1 n = n/2 . .'
        ]));

        $response->assertSuccessful()
                 ->assertJsonFragment([
                     'redirect_url' => '/',
                     'numSyntaxErrors' => 1,
                     'numContextualErrors' => 0,
                     'syntaxErrors' => ['line 1:36 missing \':\' at \'n\''],
                 ])
                 ->assertDontSeeText('nodeOrder')
                 ->assertDontSeeText('treeNodes');
    }

    /**
     * Make a post request for a code generation result with an empty
     * and ensure a 422 'Unprocessable Entity' status code is returned.
     *
     * @return void
     */
    public function testUnsuccessfulCGRequest()
    {
        $response = $this->json('POST', route('execute', [
            'type' => 'cg',
            'program' => ''
        ]));

        $response->assertStatus(422)
                 ->assertSeeText('The program field is required.');
    }
}
