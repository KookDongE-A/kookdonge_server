package com.kookdonge.kookdonge_server.waitinglist.infra.mail

import com.kookdonge.kookdonge_server.common.exception.CustomException
import com.kookdonge.kookdonge_server.waitinglist.common.WaitingListExceptionCode
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class GmailSender(
    private val javaMailSender: JavaMailSender,
) {

    private val senderEmail: String = "kookdonge@kookdonge.co.kr"

    @Async
    fun sendNotificationEmailToStartingRecruiting(email: String, clubName: String){
        val message: MimeMessage = javaMailSender.createMimeMessage()
        message.setFrom(senderEmail)
        message.setSubject("[국동이] $clubName 동아리 모집 시작 알림")
        message.setRecipients(MimeMessage.RecipientType.TO, email)

        val body : String = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body style="margin: 0; padding: 0; font-family: 'Apple SD Gothic Neo', 'Malgun Gothic', sans-serif; background-color: #f5f5f5;">
                <table width="100%" cellpadding="0" cellspacing="0" style="background-color: #f5f5f5; padding: 40px 20px;">
                    <tr>
                        <td align="center">
                            <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                                <!-- Header -->
                                <tr>
                                    <td style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 40px 30px; text-align: center;">
                                        <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: bold;">🎉 국동이</h1>
                                        <p style="margin: 10px 0 0 0; color: #ffffff; font-size: 14px; opacity: 0.9;">국민대학교 동아리 정보 플랫폼</p>
                                    </td>
                                </tr>

                                <!-- Content -->
                                <tr>
                                    <td style="padding: 40px 30px;">
                                        <h2 style="margin: 0 0 20px 0; color: #333333; font-size: 24px; font-weight: bold;">모집이 시작되었습니다!</h2>

                                        <div style="background-color: #f8f9fa; border-left: 4px solid #667eea; padding: 20px; margin: 20px 0; border-radius: 4px;">
                                            <p style="margin: 0; color: #666666; font-size: 14px; line-height: 1.6;">
                                                관심 등록하신 <strong style="color: #667eea; font-size: 18px;">$clubName</strong> 동아리의 모집이 시작되었습니다.
                                            </p>
                                        </div>

                                        <p style="margin: 20px 0; color: #666666; font-size: 14px; line-height: 1.8;">
                                            지금 바로 지원하여 새로운 동아리 생활을 시작해보세요!<br>
                                            모집 기간이 제한되어 있으니 서둘러 지원해주세요. 😊
                                        </p>

                                        <!-- CTA Button -->
                                        <table width="100%" cellpadding="0" cellspacing="0" style="margin: 30px 0;">
                                            <tr>
                                                <td align="center">
                                                    <a href="https://kookdonge.co.kr/clubs" style="display: inline-block; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: #ffffff; text-decoration: none; padding: 16px 40px; border-radius: 8px; font-size: 16px; font-weight: bold; box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);">
                                                        지금 바로 지원하기 →
                                                    </a>
                                                </td>
                                            </tr>
                                        </table>

                                        <p style="margin: 20px 0 0 0; color: #999999; font-size: 12px; line-height: 1.6;">
                                            💡 이 알림은 귀하가 관심 등록한 동아리의 모집 시작을 알려드리기 위해 발송되었습니다.
                                        </p>
                                    </td>
                                </tr>

                                <!-- Footer -->
                                <tr>
                                    <td style="background-color: #f8f9fa; padding: 30px; text-align: center; border-top: 1px solid #e9ecef;">
                                        <p style="margin: 0 0 10px 0; color: #999999; font-size: 12px;">
                                            국민대학교 동아리 정보 플랫폼 | 국동이
                                        </p>
                                        <p style="margin: 0; color: #cccccc; font-size: 11px;">
                                            © 2025 KookDongE. All rights reserved.
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
        """.trimIndent()

        message.setText(body, "UTF-8", "html")

        try{
            javaMailSender.send(message)
        } catch (e: Exception){
            throw CustomException(WaitingListExceptionCode.NOT_SEND_WAITINGLIST_NOTIFICATION)
        }
    }

}