<%@ include file="/WEB-INF/content/includes/include.jsp"%>
<%@ include file="/fbml/setProperties_DELETEME.jsp"%>
<%@ include file="/WEB-INF/content/tiles/main-header.jsp"%>

<fb:header>Fb:pronoun</fb:header>

<div>
	<a href="http://wiki.developers.facebook.com/index.php/Fb:pronoun">FBML Documentation Page</a>
	<br/>
	<br/>
	Pronoun for 12345: <fb:pronoun uid="12345" />
	<br/>
	Loggedinuser: <fb:pronoun uid="loggedinuser" />
</div>
