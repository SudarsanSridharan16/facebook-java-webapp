<%@ include file="/WEB-INF/content/includes/include.jsp"%>

<c:if test="${not empty message}">
	<div style="color:green;">
		${message }
	</div>
</c:if>

<div class="mT10">
	<div>
		Answer the poll and a notification will be sent to you.
	</div>
	<div>
		<fb:editor action="notifications" labelwidth="100">
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
