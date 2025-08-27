<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .page-content {
            font-family: 'Roboto', Arial, sans-serif;
            line-height: 1.8;
            margin: 0;
            padding: 0;
            background-color: #f4f7fa;
            color: #333;
        }
        .page-container {
            max-width: 1200px;
            margin: 60px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        h1 {
            color: #1a73e8;
            font-size: 2.5em;
            margin-bottom: 20px;
            text-align: center;
        }
        h2 {
            color: #1a73e8;
            font-size: 1.8em;
            margin-top: 30px;
            margin-bottom: 15px;
            border-bottom: 2px solid #e8f0fe;
            padding-bottom: 8px;
        }
        h3 {
            color: #333;
            font-size: 1.4em;
            margin-top: 25px;
            margin-bottom: 10px;
        }
        p {
            margin: 12px 0;
            font-size: 1.1em;
            color: #555;
        }
        ul {
            margin: 15px 0;
            padding-left: 25px;
        }
        li {
            margin-bottom: 10px;
            font-size: 1.05em;
            color: #555;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 25px 0;
            background-color: #fafafa;
            border-radius: 8px;
            overflow: hidden;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }
        th {
            background-color: #1a73e8;
            color: #fff;
            font-weight: 600;
        }
        td {
            font-size: 1em;
            color: #444;
        }
        .return-button {
            display: inline-block;
            margin-top: 30px;
            padding: 12px 25px;
            background-color: #1a73e8;
            color: #fff;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 1.1em;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .return-button:hover {
            background-color: #1557b0;
        }
        .info-block {
            background-color: #e8f0fe;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
        }
        .text-highlight {
            font-weight: 600;
            color: #1a73e8;
        }
    </style>
    <div class="page-content">
        <div class="page-container">
            <h1 class="page-title h3"><@spring.message "privacy.policy.title"/></h1>
            <p><strong><@spring.message "privacy.policy.effective.date"/></strong> <@spring.message "privacy.policy.effective.date.value"/></p>
            <p><@spring.message "privacy.policy.intro"/></p>

            <h2><@spring.message "privacy.policy.section.1.title"/></h2>
            <p><@spring.message "privacy.policy.section.1.content"/></p>
            <div class="info-block">
                <p><strong><@spring.message "privacy.policy.contact.title"/></strong><br>
                    <@spring.message "privacy.policy.contact.email"/>: <a class="text-highlight" href="mailto:mathbilim@gmail.com">mathbilim@gmail.com</a>
            </div>

            <h2><@spring.message "privacy.policy.section.2.title"/></h2>
            <p><@spring.message "privacy.policy.section.2.intro"/></p>
            <ul>
                <li><strong><@spring.message "privacy.policy.data.provided"/></strong> <@spring.message "privacy.policy.data.provided.details"/></li>
                <li><strong><@spring.message "privacy.policy.data.technical"/></strong> <@spring.message "privacy.policy.data.technical.details"/></li>
                <li><strong><@spring.message "privacy.policy.data.analytical"/></strong> <@spring.message "privacy.policy.data.analytical.details"/></li>
                <li><strong><@spring.message "privacy.policy.data.third.party"/></strong> <@spring.message "privacy.policy.data.third.party.details"/></li>
            </ul>

            <h2><@spring.message "privacy.policy.section.3.title"/></h2>
            <p><@spring.message "privacy.policy.section.3.intro"/></p>
            <ul>
                <li><strong><@spring.message "privacy.policy.purpose.service"/></strong> <@spring.message "privacy.policy.purpose.service.details"/></li>
                <li><strong><@spring.message "privacy.policy.purpose.communication"/></strong> <@spring.message "privacy.policy.purpose.communication.details"/></li>
                <li><strong><@spring.message "privacy.policy.purpose.analytics"/></strong> <@spring.message "privacy.policy.purpose.analytics.details"/></li>
                <li><strong><@spring.message "privacy.policy.purpose.security"/></strong> <@spring.message "privacy.policy.purpose.security.details"/></li>
                <li><strong><@spring.message "privacy.policy.purpose.legal"/></strong> <@spring.message "privacy.policy.purpose.legal.details"/></li>
            </ul>

            <h2><@spring.message "privacy.policy.section.4.title"/></h2>
            <p><@spring.message "privacy.policy.section.4.intro"/></p>
            <ul>
                <li><strong><@spring.message "privacy.policy.basis.contract"/></strong> <@spring.message "privacy.policy.basis.contract.details"/></li>
                <li><strong><@spring.message "privacy.policy.basis.consent"/></strong> <@spring.message "privacy.policy.basis.consent.details"/></li>
                <li><strong><@spring.message "privacy.policy.basis.interest"/></strong> <@spring.message "privacy.policy.basis.interest.details"/></li>
                <li><strong><@spring.message "privacy.policy.basis.legal"/></strong> <@spring.message "privacy.policy.basis.legal.details"/></li>
            </ul>

            <h2><@spring.message "privacy.policy.section.5.title"/></h2>
            <table>
                <tr>
                    <th><@spring.message "privacy.policy.table.data.category"/></th>
                    <th><@spring.message "privacy.policy.table.source"/></th>
                    <th><@spring.message "privacy.policy.table.purpose"/></th>
                    <th><@spring.message "privacy.policy.table.basis"/></th>
                    <th><@spring.message "privacy.policy.table.retention"/></th>
                    <th><@spring.message "privacy.policy.table.transfer"/></th>
                </tr>
                <tr>
                    <td><@spring.message "privacy.policy.table.row1.data"/></td>
                    <td><@spring.message "privacy.policy.table.row1.source"/></td>
                    <td><@spring.message "privacy.policy.table.row1.purpose"/></td>
                    <td><@spring.message "privacy.policy.table.row1.basis"/></td>
                    <td><@spring.message "privacy.policy.table.row1.retention"/></td>
                    <td><@spring.message "privacy.policy.table.row1.transfer"/></td>
                </tr>
                <tr>
                    <td><@spring.message "privacy.policy.table.row2.data"/></td>
                    <td><@spring.message "privacy.policy.table.row2.source"/></td>
                    <td><@spring.message "privacy.policy.table.row2.purpose"/></td>
                    <td><@spring.message "privacy.policy.table.row2.basis"/></td>
                    <td><@spring.message "privacy.policy.table.row2.retention"/></td>
                    <td><@spring.message "privacy.policy.table.row2.transfer"/></td>
                </tr>
                <tr>
                    <td><@spring.message "privacy.policy.table.row3.data"/></td>
                    <td><@spring.message "privacy.policy.table.row3.source"/></td>
                    <td><@spring.message "privacy.policy.table.row3.purpose"/></td>
                    <td><@spring.message "privacy.policy.table.row3.basis"/></td>
                    <td><@spring.message "privacy.policy.table.row3.retention"/></td>
                    <td><@spring.message "privacy.policy.table.row3.transfer"/></td>
                </tr>
                <tr>
                    <td><@spring.message "privacy.policy.table.row4.data"/></td>
                    <td><@spring.message "privacy.policy.table.row4.source"/></td>
                    <td><@spring.message "privacy.policy.table.row4.purpose"/></td>
                    <td><@spring.message "privacy.policy.table.row4.basis"/></td>
                    <td><@spring.message "privacy.policy.table.row4.retention"/></td>
                    <td><@spring.message "privacy.policy.table.row4.transfer"/></td>
                </tr>
                <tr>
                    <td><@spring.message "privacy.policy.table.row5.data"/></td>
                    <td><@spring.message "privacy.policy.table.row5.source"/></td>
                    <td><@spring.message "privacy.policy.table.row5.purpose"/></td>
                    <td><@spring.message "privacy.policy.table.row5.basis"/></td>
                    <td><@spring.message "privacy.policy.table.row5.retention"/></td>
                    <td><@spring.message "privacy.policy.table.row5.transfer"/></td>
                </tr>
            </table>

            <h2><@spring.message "privacy.policy.section.6.title"/></h2>
            <p><@spring.message "privacy.policy.section.6.intro"/></p>
            <ul>
                <li><strong><@spring.message "privacy.policy.transfer.providers"/></strong> <@spring.message "privacy.policy.transfer.providers.details"/></li>
                <li><strong><@spring.message "privacy.policy.transfer.partners"/></strong> <@spring.message "privacy.policy.transfer.partners.details"/></li>
                <li><strong><@spring.message "privacy.policy.transfer.authorities"/></strong> <@spring.message "privacy.policy.transfer.authorities.details"/></li>
            </ul>
            <p><@spring.message "privacy.policy.transfer.assurance"/></p>

            <h2><@spring.message "privacy.policy.section.7.title"/></h2>
            <p><@spring.message "privacy.policy.section.7.content"/></p>

            <h2><@spring.message "privacy.policy.section.8.title"/></h2>
            <p><@spring.message "privacy.policy.section.8.intro"/></p>
            <ul>
                <li><strong><@spring.message "privacy.policy.rights.access"/></strong> <@spring.message "privacy.policy.rights.access.details"/></li>
                <li><strong><@spring.message "privacy.policy.rights.correction"/></strong> <@spring.message "privacy.policy.rights.correction.details"/></li>
                <li><strong><@spring.message "privacy.policy.rights.deletion"/></strong> <@spring.message "privacy.policy.rights.deletion.details"/></li>
                <li><strong><@spring.message "privacy.policy.rights.restriction"/></strong> <@spring.message "privacy.policy.rights.restriction.details"/></li>
                <li><strong><@spring.message "privacy.policy.rights.portability"/></strong> <@spring.message "privacy.policy.rights.portability.details"/></li>
                <li><strong><@spring.message "privacy.policy.rights.objection"/></strong> <@spring.message "privacy.policy.rights.objection.details"/></li>
                <li><strong><@spring.message "privacy.policy.rights.consent.withdrawal"/></strong> <@spring.message "privacy.policy.rights.consent.withdrawal.details"/></li>
            </ul>
            <p><@spring.message "privacy.policy.rights.contact"/> <a class="text-highlight" href="mailto:privacy@mathbilim.com">privacy@mathbilim.com</a>.</p>

            <h2><@spring.message "privacy.policy.section.9.title"/></h2>
            <p><@spring.message "privacy.policy.section.9.content"/></p>

            <h2><@spring.message "privacy.policy.section.10.title"/></h2>
            <p><@spring.message "privacy.policy.section.10.content"/></p>

            <h2><@spring.message "privacy.policy.section.11.title"/></h2>
            <p><@spring.message "privacy.policy.section.11.intro"/></p>
            <div class="info-block">
                <p><@spring.message "privacy.policy.contact.email"/>: <a class="text-highlight" href="mailto:mathbilim@gmail.com">mathbilim@gmail.com</a><br>
                    <@spring.message "privacy.policy.contact.address"/>: <@spring.message "privacy.policy.contact.address.value"/></p>
            </div>

            <a href="/" class="return-button"><@spring.message "privacy.policy.back.home"/></a>
        </div>
    </div>
</@main.layout>