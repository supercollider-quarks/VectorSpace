// ©2009 Miguel Negr‹o, Fredrik Olofsson
// GPLv3 - http://www.gnu.org/licenses/gpl-3.0.html

//Vector class for vectors of any dimension, with optimized classes for 2D and 3D.


Vector[float] : FloatArray {
	
	species {^this.class}

	*rand{ |size = 2 ,lo =0.0, hi = 1.0|
		^size.collect{ rrand(lo,hi) }.as(Vector)
	}
		
	*rand2D{ |xlo,xhi,ylo,yhi|		
		^Vector[rrand(xlo,xhi),rrand(ylo,yhi)]	
	}	
	
	*canonB{ |i,size|
		^size.collect{ |j| (i==j).binaryValue }.as(Vector)
	}
	
	//usual notation up to 3D vectors
	x{ ^this.at(0) }
	
	y{ ^this.at(1) }
	
	z{ ^this.at(2) }	
	
	//inner product
	<|>{ |vector|
		^this.size.collect{ |i|
			this[i] * vector[i]
		}.sum
	}
	
	//outerProduct
	cross{ |vector| 
		var a1,a2,a3,b1,b2,b3;
		#a1,a2,a3 = this;
		#b1,b2,b3 = vector;
		^Vector[(a2*b3)-(a3*b2),(a3*b1)-(a1*b3),(a1*b2)-(a2*b1)]
	}

	norm{
		^this.sum{|x| x*x}.sqrt
	}
	
	dist{ |vector|
		^(this-vector).norm
	}
		
	theta{ 
		^atan2(this.at(1), this.at(0))
	}
	
	normalize{
		^this/this.norm
	}
	
	limit { |max|
		if(this.norm>max, {^this.normalize*max})
	}
	
	isOrthogonal{ |vector|
		^((this <|> vector)==0)
	}

	asPoint{
		^Point(this[0],this[1])
	}
	
	asVector{ ^this }		
}

//--2d vector optimised for speed
Vector2D[float] : Vector {
	
	norm {
		^this[0].sumsqr(this[1]).sqrt
	}
	
	dist { |vec| 
		^(vec[0]-this[0]).hypot(vec[1]-this[1])
	}
	
	<|> { |vec| 
		^(this[0]*vec[0])+(this[1]*vec[1])
	}
	
}

//--3d vector optimised for speed
Vector3D[float] : Vector {
	
	norm {
		^(this[0].sumsqr(this[1])+this[2].pow(2)).sqrt
	}
	
	dist { |vec| 
		^(vec[0]-this[0]).hypot((vec[1]-this[1]).hypot(vec[2]-this[2]))
	}
	
	<|> { |vec| 
		^(this[0]*vec[0])+(this[1]*vec[1])+(this[2]*vec[2])
	}
}


+ Point {	
	
	asVector{
		^Vector[this.x,this.y]
	}
}

