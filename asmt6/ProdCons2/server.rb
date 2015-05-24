=begin

Filename: server.rb
Author: Eliot Cowley
Class: CS-377
Date: 5/12/15
Professor: Marc Smith
Command-line instructions: ruby server.rb
Run server.rb first in one terminal, then producer.rb in another, then 
consumer.rb in another

This is the server process that enables Rinda to work.

=end

require 'rinda/tuplespace'
require 'rinda/rinda'
 
URI = "druby://localhost:67671"
DRb.start_service(URI, Rinda::TupleSpace.new)
DRb.thread.join
