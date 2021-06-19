<% ui.decorateWith("appui", "standardEmrPage") %>

<%=ui.resourceLinks()%>
<% ui.includeCss("nmrsreports", "bootstrap.css") %>
<% ui.includeJavascript("nmrsreports", "bootstrap.js") %>

<style>
.card-header {
    padding: 0.75rem 1.25rem;
    margin-bottom: 0;
    background-color: #2f1c55;
    border-bottom: 1px solid rgba(0, 0, 0, 0.125);
    color: #fff;
}
.stats{
    color: #363463 !important;
    text-decoration: none !important;
}


h3.mb-0 a:link
{
    font-weight: bold;
    font-size: 30px;
}


.flex-md-row  {
    background-clip: border-box;
    border-radius: .375rem;
    box-shadow: 0 7px 14px 0 rgb(65 69 88 / 10%), 0 3px 6px 0 rgb(0 0 0 / 7%);
    border: 1px solid #edf2f9 !important;
    border: 1px solid #6c757d !important;
}



.card-header h5 {
    color: #fff;
    clear: both;
    margin: 10px 0;
    font-weight: normal;
}
div.card-body a:link {
    color: #363463 !important;
    text-decoration: none !important;
}
div.card-body a:visited {
    color: #363463 !important;
    text-decoration: none !important;
}
div.card-body a:hover {
    color: #363463 !important;
    text-decoration: none !important;
}
</style>
<h4 class="text-center">
    NMRS Reportining module
</h4>



${ui.includeFragment("nmrsreports", "users")}