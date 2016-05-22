import javax.print.attribute.standard.MediaSize.Other;

public class Pair {
	
	String a, b;
	int value;
	
	public Pair(String a, String b, int value) {
		this.a = a;
		this.b = b;
		this.value = value;
	}
	
	@Override
	public int hashCode() 
	{
		return this.a.hashCode() + this.b.hashCode();
	}
	
	@Override
	public boolean equals(Object o) 
	{
		Pair other = (Pair) o;
		//System.out.println("Comparing: " + this.a +" "+this.b + " - " +other.a +" "+other.b );
		return ((this.a.equals(other.a) && this.b.equals(other.b)) ||
				(this.a.equals(other.b) && this.b.equals(other.a)));
	}
}
