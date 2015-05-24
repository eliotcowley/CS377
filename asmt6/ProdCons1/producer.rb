=begin

Filename: producer.rb
Author: Eliot Cowley
Class: CS-377
Date: 5/12/15
Professor: Marc Smith
Command-line instructions: ruby producer.rb
Run server.rb first in one terminal, then producer.rb in another, then 
consumer.rb in another

This program is modelled off of the Producer process on slide 9 of the Rinda
lecture. Instead of having a separate "init" program, I just have producer
initialize all the variables. buf is the integer that represents the buffer.
n is the capacity of the producer and consumer arrays, ensuring that the program
doesn't go on forever. p is the producer count which tells the program at which
position in the array it is at, and c is the consumer count which performs a
similar function for the consumer. Once initialization is complete, it enters
its main loop. While p < n, it checks if p is equal to c. When it is, it writes
the value at position p of its array to the buffer, puts it to the screen, 
and increments p, thereby handing control off to the consumer.

=end

require 'rinda/rinda'

URI = "druby://localhost:67671"
DRb.start_service
ts = Rinda::TupleSpaceProxy.new(DRbObject.new(nil, URI))

# buffer
ts.write(["buf", nil])

# capacity of producer/consumer arrays
ts.write(["n", 80])

# producer counter
ts.write(["p", 0])

# consumer counter
ts.write(["c", 0])

# fill out array
array = Array.new(ts.read(["n", nil])[1])
i = 0
while i < array.length
  array[i] = i
  i = i + 1
end

# write values to buffer
puts "Producer looping..."
loop do
	while (ts.read(["p", nil])[1] < ts.read(["n", nil])[1])
	  if ts.read(["p", nil])[1] === ts.read(["c", nil])[1]
	    p = ts.take(["p", nil])[1]
	    ts.write(["buf", array[p]])
	    puts("Value #{array[p]} produced")
	    p = p + 1
	    ts.write(["p", p])
	  end
	end
end

