package engine.sp.custom.function;

import engine.sp.ScriptProcessor;

/**
 * Uses the <code>indexOf</code> function to identify the first index of a 
 * token <code>ws:SearchToken</code> in some search text <code>ws:SearchText</code>,
 * storing the result in the working storage variable <code>ws:SearchFirstIndex</code>
 */
public class FirstIndexOf implements FunctionCommand {

	private ScriptProcessor scriptProcessor;
	
	public FirstIndexOf(ScriptProcessor scriptProcessor) {
		this.scriptProcessor = scriptProcessor;
	}
	
	@Override
	public void exec() {
		String searchToken = (String) this.scriptProcessor.getWS().get("SearchToken");
		String searchText = (String) this.scriptProcessor.getWS().get("SearchText");
		Integer position = null;
		if(searchText == null || searchToken == null) {
			position = -1;
		} else {
			position = searchText.indexOf(searchToken);
		}
		this.scriptProcessor.getWS().put("SearchFirstIndex", position.toString());
	}

}
