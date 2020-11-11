

import java.io.Serializable;

public class ResponseCorrectAnswer implements Serializable {

   
	private static final long serialVersionUID = 1L;

    private String response;

    public ResponseCorrectAnswer(String r) {
        this.response = r; 
    }

	@Override
	public String toString() {
		return response;
	}
	public void setName(String n){
    	this.response = n;
	}
	public String getName(){
    	return this.response;
	}

	public boolean equals(Object o) {
    	if (!(o instanceof ResponseCorrectAnswer))
    		return false;
    	else {
    		ResponseCorrectAnswer y = (ResponseCorrectAnswer) o;
    		return y.getName().equals(this.getName());
		}

	}

	public int hashCode() {
		return response.hashCode();
	}

    public Boolean compare(Object a) {
		if (this == a)
			return true;
		if (a == null)
			return false;
		if (getClass() != a.getClass())
			return false;
		ResponseCorrectAnswer other = (ResponseCorrectAnswer) a;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}
   
    public void display(Output out) {
   
    	out.display(this.response);
    }

}
