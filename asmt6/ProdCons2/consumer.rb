=begin

Filename: consumer.rb
Author: Eliot Cowley
Class: CS-377
Date: 5/12/15
Professor: Marc Smith
Command-line instructions: ruby consumer.rb
Run server.rb first in one terminal, then producer.rb in another, then 
consumer.rb in another

This program is similar to the consumer program on slide 12 of the Linda
lecture. It reads in n, the capacity of the buffer, and tries to consume values
from the buffer. So the program doesn't go on ad infinitum, I limit it to 
consuming n*2 items. Using the semaphore full (in this Rinda implementation, 
tuples and semaphores are the same thing), it waits until producer signals that 
it has written a value, then takes a value from the buffer at position front, 
printing it to the screen. Front is then incremented (mod n, so that we don't 
get an out of bounds exception) and the empty semaphore is signalled.

=end

require 'rinda/rinda'

URI = "druby://localhost:67671"
DRb.start_service
ts = Rinda::TupleSpaceProxy.new(DRbObject.new(nil, URI))

# get n
n = ts.read(["n", Numeric])[1]

# consume
max = n*2
max.times do
  ts.take(["full"])
  front = ts.take(["front", Numeric])[1]
  result = ts.take(["buf", front, Numeric])[2]
  puts("Consumed value #{result}")
  front = (front+1)%n
  ts.write(["front", front])
  ts.write(["empty"])
end
