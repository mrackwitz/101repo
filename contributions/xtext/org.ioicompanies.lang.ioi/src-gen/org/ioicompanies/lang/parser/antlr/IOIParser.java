/*
* generated by Xtext
*/
package org.ioicompanies.lang.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.ioicompanies.lang.services.IOIGrammarAccess;

public class IOIParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private IOIGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected org.ioicompanies.lang.parser.antlr.internal.InternalIOIParser createParser(XtextTokenStream stream) {
		return new org.ioicompanies.lang.parser.antlr.internal.InternalIOIParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Model";
	}
	
	public IOIGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(IOIGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
