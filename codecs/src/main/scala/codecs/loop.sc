def repeatUntil(command: => Unit)(condition: => Boolean):Unit={
  command
  if !condition then repeatUntil(command)(condition)
}

var x=0;
var  y:BigInt=3;
var z=3
repeatUntil {
  x = x + 1
  y = y * 2
}(x==50)
x
y