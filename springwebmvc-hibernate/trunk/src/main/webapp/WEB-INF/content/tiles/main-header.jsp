<%@ include file="/WEB-INF/content/includes/include.jsp"%>
<fb:google-analytics uacct="UA-2694507-3" />

<fb:if-is-app-user>
	&nbsp;
	<fb:else>
		<div style="float: right;">
			<a href="http://www.facebook.com/login.php?api_key=${apiKey}&v=1.0" requirelogin=1>
				<img src="http://static.ak.facebook.com/images/devsite/facebook_login.gif">
			</a>
		</div>
	</fb:else>
</fb:if-is-app-user>

<fb:dashboard />

<fb:tabs>
    <fb:tab-item href="${canvasUrl}/" title="Welcome" selected="${welcomeSelected}"/>
    <fb:tab-item href="${canvasUrl}/invite" title="Invite Friends" selected="${inviteSelected}"/>
    <fb:tab-item href="${canvasUrl}/fbml-tags" title="FBML Tags" selected="${fbmlTagsSelected}"/>
</fb:tabs>