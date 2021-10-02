# Name: localhost code.py
# Author: Varun Pereira
# Date: 13/10/2020

import http.server
import socketserver

# creates a local server, so the data that is
# successfully transmitted from microbit to pc,
# and stored in csv files, can only be accessed on this computer,
# via this web server once this program runs.

PORT = 8080
Handler = http.server.SimpleHTTPRequestHandler

with socketserver.TCPServer(("", PORT), Handler) as httpd:
    print("serving at port", PORT)
    httpd.serve_forever()