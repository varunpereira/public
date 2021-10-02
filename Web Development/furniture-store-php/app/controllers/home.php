<?php

class Home extends Controller
{
    function index()
    {
        $data['page_title'] = "Home"; 
        $this->view("home", $data);
    }
}
