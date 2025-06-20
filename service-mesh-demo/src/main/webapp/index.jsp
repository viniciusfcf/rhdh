<%@ page import="java.net.*, java.io.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Service Mesh Demo - JSP + Servlets</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .service-box { padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        .service-a { background-color: #e0f0ff; color: #004080; }
        .service-b { background-color: #e0ffe0; color: #006600; }
        .error { color: red; }
    </style>
</head>
<body>

    <h1>🕸️ Service Mesh Demo (JSP + Servlets)</h1>
    <p>Este exemplo faz chamadas a dois servlets simulando dois serviços passando por um Service Mesh.</p>

    <%
        String[] services = { "serviceA", "serviceB" };
        String[] cssClasses = { "service-a", "service-b" };
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        for (int i = 0; i < services.length; i++) {
            String serviceUrl = baseUrl + "/" + services[i];
            String responseText = "";
            boolean error = false;

            try {
                URL url = new URL(serviceUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                responseText = content.toString();
            } catch (Exception e) {
                error = true;
                responseText = "Error calling " + serviceUrl + ": " + e.getMessage();
            }
    %>

    <div class="service-box <%= cssClasses[i] %>">
        <h2>Response from <%= services[i] %>:</h2>
        <p class="<%= error ? "error" : "" %>"><%= responseText %></p>
    </div>

    <%
        }
    %>

</body>
</html>
