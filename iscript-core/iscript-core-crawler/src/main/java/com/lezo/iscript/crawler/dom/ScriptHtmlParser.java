package com.lezo.iscript.crawler.dom;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class ScriptHtmlParser {

	public static ScriptDocument parser(org.jsoup.nodes.Document document) {
		ScriptDocument scriptDocument = new ScriptDocument(document);
		List<Node> childList = document.childNodes();
		for (Node child : childList) {
			doCopy(child, scriptDocument, scriptDocument);
		}
		return scriptDocument;
	}

	public static void doCopy(Node source, ScriptElement parent, final ScriptDocument scriptDocument) {
		ScriptElement newChild = scriptDocument.createElement(source);
		parent.appendChild(newChild);
		List<Node> childList = source.childNodes();
		for (Node child : childList) {
			doCopy(child, newChild, scriptDocument);
		}
	}
}
