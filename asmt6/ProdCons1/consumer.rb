=begin

Filename: consumer.rb
Author: Eliot Cowley
Class: CS-377
Date: 5/12/15
Professor: Marc Smith
Command-line instructions: ruby consumer.rb
Run server.rb first in one terminal, then producer.rb in another, then 
consumer.rb in another

This program is modelled off of the Consumer process on slide 9 of the Rinda
lecture. First it initializes an empty array of size n. Next, it begins its main
loop. While c, the consumer counter, is less than n, if p is ever greater than
c, the consumer count, it reads in the value in the buffer and stores it in 
position c in its array, putting it to the screen as well. Then it increments c
and writes it back to tuple space, thereby handing control off to the producer.

=end

require 'rinda/rinda'

URI = "druby://localhost:67671"
DRb.start_service
ts = Rinda::TupleSpaceProxy.new(DRbObject.new(nil, URI))

# initialize array
b = Array.new(ts.read(["n", nil])[1])

# consume
puts("Consumer looping...")
loop do
	while (ts.read(["c", nil])[1] < ts.read(["n", nil])[1])
	  if ts.read(["p", nil])[1] > ts.read(["c", nil])[1]
	    c = ts.take(["c", nil])[1]
	    buf = ts.take(["buf", Numeric])[1]
	    b[c] = buf
	    puts("Value #{b[c]} consumed")
	    c = c + 1
	    ts.write(["c", c])
	  end
	end
end
