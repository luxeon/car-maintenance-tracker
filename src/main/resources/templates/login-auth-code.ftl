<#ftl strip_whitespace=true>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${appName} Login Code</title>
    <style>
        body {
            font-family: "Segoe UI", Helvetica, Arial, sans-serif;
            background-color: #f5f7fb;
            color: #1f2a37;
            margin: 0;
            padding: 32px;
        }

        .card {
            background-color: #ffffff;
            border-radius: 10px;
            padding: 32px;
            max-width: 520px;
            margin: 0 auto;
            box-shadow: 0 8px 24px rgba(15, 23, 42, 0.08);
        }

        .cta {
            display: inline-block;
            background-color: #2563eb;
            color: #ffffff;
            padding: 16px 24px;
            border-radius: 8px;
            font-size: 24px;
            letter-spacing: 4px;
            text-decoration: none;
        }

        .footer {
            margin-top: 32px;
            color: #6b7280;
            font-size: 12px;
        }
    </style>
</head>
<body>
<div class="card">
    <p>Hi,</p>
    <p>We received a request to sign in to <strong>${appName}</strong>.</p>
    <p>Use the authentication code below to continue:</p>
    <p>
        <span class="cta">${authCode}</span>
    </p>
    <p class="footer">
        This code expires in 5 minutes. If you didn't request it, you can safely ignore this email.
    </p>
    <p class="footer">
        Cheers,<br>
        The ${appName} Team
    </p>
</div>
</body>
</html>
