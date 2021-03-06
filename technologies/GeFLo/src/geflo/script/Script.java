package geflo.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Script {
	
	private List<Token> tokens = new ArrayList<Token>();
	
	public Token get(int i) {
		return tokens.get(i);
	}
	
	public int size() {
		return tokens.size();
	}
	
	public void add(Token keyword) {
		tokens.add(keyword);
	}
	
	public List<Token> getTokens() {
		return tokens;
	}
	
	public static Script parseJSONString(String input) {
		return parseJSON(new JSONTokener(input));
	}
	
	public static Script parseJSONStream(InputStream stream) {
		return parseJSON(new JSONTokener(stream));
	}
	
	public static Script parseJSONFile(File file) throws FileNotFoundException {
		return parseJSON(new JSONTokener(new FileInputStream(file)));
	}
	
	protected static Script parseJSON(final JSONTokener tokener) {
		try {
			final JSONObject	json	= new JSONObject(tokener);
			final JSONArray		tokens	= json.getJSONArray("tokens");
			if (tokens == null) {
				throw new RuntimeException("There is no key 'tokens'!");
			}
			
			final Script script = new Script();
			for (int i=0; i<tokens.length(); i++) {
				JSONObject jsonToken = tokens.getJSONObject(i);
				Token token = new Token(
						jsonToken.getInt("line"),
						jsonToken.getInt("i"),
						jsonToken.getString("text")
					);
				script.add(token);
			}
			return script;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
}
