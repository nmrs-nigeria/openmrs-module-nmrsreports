<%

    def appFrameworkService = context.getService(context.loadClass("org.openmrs.module.appframework.service.AppFrameworkService"))
    def overviews = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.overview")
    def monitoringReports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.monitoring")
    def dataQualityReports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.dataquality")
    def dataExports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.dataexport")
    def contextModel = [:]
%>

<div class="row md-12">
    <div class="col-md-3">
        <div class="card flex-md-row mb-4 box-shadow h-md-250">
            <div class="card-body d-flex flex-column align-items-start">
                <strong class="d-inline-block mb-2 stats">Total TX CURR</strong>

                <h3 class="mb-0">
                    <a class=" text-dark" href="#">10,000</a>
                </h3>
            </div>

        </div>
    </div>

    <div class="col-md-3">
        <div class="card flex-md-row mb-4 box-shadow h-md-250">
            <div class="card-body d-flex flex-column align-items-start">
                <strong class="d-inline-block mb-2 stats">Weekly TX New</strong>

                <h3 class="mb-0">
                    <a class=" text-dark" href="#">2,000</a>
                </h3>
            </div>
        </div>
    </div>

    <div class="col-md-3">
        <div class="card flex-md-row mb-4 box-shadow h-md-250">
            <div class="card-body d-flex flex-column align-items-start">
                <strong class="d-inline-block mb-2 stats">Total PBS Capture</strong>

                <h3 class="mb-0">
                    <a class=" text-dark" href="#">2,000</a>
                </h3>
            </div>
        </div>
    </div>

    <div class="col-md-3">
        <div class="card flex-md-row mb-4 box-shadow h-md-250">
            <div class="card-body d-flex flex-column align-items-start">
                <strong class="d-inline-block mb-2 stats">Total HTS</strong>

                <h3 class="mb-0">
                    <a class=" text-dark" href="#">2,000</a>
                </h3>
            </div>
        </div>
    </div>

</div>
<% if (metadataModuleVersion == "1.0.2-SNAPSHOT") { %>
<div id="accordion">
    <div class="card">
        <div class="card-header" id="headingOne" data-toggle="collapse" data-target="#collapseOne">
            <h5 class="mb-0">

                <% if (monitoringReports) { %>
                <p>${ui.message("reportingui.reportsapp.monitoringReports")}</p>
                <% } %>
            </h5>
        </div>

        <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
            <div class="card-body">
                <% if (monitoringReports) { %>
                <table>
                    <% monitoringReports.each { %>
                    <tr>
                        <th>${
                                ui.includeFragment("uicommons", "extension", [extension: it, contextModel: contextModel])}</th>
                    </tr>
                    <% } %>
                </table>
                <% } %>
            </div>
        </div>
    </div>

    <div class="card">
        <div class="card-header" id="headingTwo" collapsed" data-toggle="collapse" data-target="#collapseTwo">
        <h5 class="mb-0">
            <% if (dataQualityReports) { %>
            <p>${ui.message("reportingui.reportsapp.dataQualityReports")}</p>
            <% } %>
        </h5>
    </div>

    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
        <div class="card-body">
            <% if (dataQualityReports) { %>
            <table>

                <% dataQualityReports.each { %>
                <tr>
                    <th>
                        ${ui.includeFragment("uicommons", "extension", [extension: it, contextModel: contextModel])}
                    </th>
                </tr>
                <% } %>
            </table>
            <% } %>
        </div>
    </div>
</div>
</div>


<% } else { %>
<div class="alert alert-danger text-center" role="alert">
    <b>An old version of Nmrs Metadata data module(${metadataModuleVersion}) is install</b> <br>Please click <a
        href="#">here</a> to update
</div>
<% } %>

