<%@ include file="/WEB-INF/content/includes/include.jsp"%>

<div>
welcome <fb:name uid="loggedinuser" capitalize="true" useyou="false" firstnameonly="true" />!
</div>

<div>
	<div>
		Answer the poll and a notification will be sent to yourself?
	</div>
	<div style="margin-top: 10px;">
		<fb:editor action="welcome" labelwidth="100">
			<input type="hidden" name="methodName" value="sendNotificationToOneUser">
			<fb:editor-custom label="Does developing for facebook is easier with facebook-java-webapp?">
				<select name="state">
					<option value="Yes" selected>Yes</option>
					<option value="No">No</option>
					<option value="Maybe">Maybe</option>
				</select>
			</fb:editor-custom>
			<fb:editor-buttonset>
				<fb:editor-button value="Submit" />
				<fb:editor-cancel />
			</fb:editor-buttonset>
		</fb:editor>
	</div>
</div>

<div>
	<div>
		Publish a feed through a Form
	</div>
	<div>
		<form fbtype="feedStory" action="${callbackUrl }/welcome">
			<input type="hidden" name="methodName" value="publishToMiniFeedFromFeedForm">
			<input type="text" name="status" />
			<input type="submit" value="Publish" label="Publish" />
		</form>
	</div>
</div>

<div>
	<div>
		Publish a feed through an API method call
	</div>
	<div>
		<fb:editor action="welcome" labelwidth="100">
			<input type="hidden" name="methodName" value="publishToMiniFeed">
			<input type="submit" value="Send_API_Call" label="Send API Call">
		</fb:editor>
	</div>
</div>
