<%@ include file="/WEB-INF/content/includes/include.jsp"%>
<%@ include file="/fbml/setProperties_DELETEME.jsp"%>
<%@ include file="/WEB-INF/content/tiles/main-header.jsp"%>

<fb:header>Fb:user</fb:header>

<div>
	<a href="http://wiki.developers.facebook.com/index.php/Fb:user">FBML Documentation Page</a>
	<br/>
	<br/>
	<fb:user uid="loggedinuser" >
		This is Loggedinuser user's information. It should only be shown to people who have not been blocked by Loggedin user.
	</fb:user>
	<br/>
	<fb:user uid="4" >
		This is user 4's information. It should only be shown to people who have not been blocked by user 4.
	</fb:user>
</div>
