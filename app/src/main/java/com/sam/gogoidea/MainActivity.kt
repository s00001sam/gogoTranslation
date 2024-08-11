package com.sam.gogoidea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import com.sam.gogozoo.ui.theme.IdeaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var model: GenerativeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IdeaTheme {
                MyRouter(
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
        }
    }

    fun test() {
        // sam00 test
        lifecycleScope.launch {
            val prompt = """
            目前在進行 brainstorming
            目前為「發表意見」階段
            主題為：教育軟體功能
            副主題：無
            概述為：要實作一個教育軟體，可以讓使用者彼此交換技能，彼此都是彼此的老師
            你的個性：一位極度想分享技能的人
            提供：每個想法不超過 10 字，至多 5 個想法，為每個想法加上 tag，tag 為 1 個英文單字，為每個想法加上優缺點，優點和缺點各自限制為 10 個字以內。
            回傳文字：轉成 Json Array 格式（欄位為 idea, tag, advantage, disadvantage）
            補充規則：每個想法的 tag 不可重複，每個 tag 都要有對應的想法
            """.trimIndent()

            val prompt2 = """
            目前在進行 brainstorming
            目前為「發表意見」階段
            主題：教育軟體功能 的 技能樹圖
            概述：針對技能樹圖的功能發想
            你的個性：一位極度想分享技能的人
            提供：每個想法不超過 10 字，至多 5 個想法，為每個想法加上 tag，tag 為 1 個英文單字，為每個想法加上優缺點，優點和缺點各自限制為 10 個字以內。
            回傳文字：轉成 Json Array 格式（欄位為 idea, tag, advantage, disadvantage）
            補充規則：每個想法的 tag 不可重複，每個 tag 都要有對應的想法
            """.trimIndent()
            val res = model.generateContent(prompt)

            Timber.d("sam00 text1=${res.text}")
        }

        lifecycleScope.launch {
            val prompt = """
            目前在進行 brainstorming
            目前為「發表意見」階段
            主題為：教育軟體功能
            副主題：無
            概述為：要實作一個教育軟體，可以讓使用者彼此交換技能，彼此都是彼此的老師
            你的個性：一位想學習技能但不知道可以學什麼的人
            提供：每個想法不超過 10 字，至多 5 個想法，為每個想法加上 tag，tag 為 1 個英文單字，為每個想法加上優缺點，優點和缺點各自限制為 10 個字以內。
            回傳文字：轉成 Json Array 格式（欄位為 idea, tag, advantage, disadvantage）
            補充規則：每個想法的 tag 不可重複，每個 tag 都要有對應的想法
            """.trimIndent()

            val prompt2 = """
            目前在進行 brainstorming
            目前為「給分階段」階段
            主題為：教育軟體功能
            總共有下想法，每個想法你能給 1~5 分，5 分為最高分，1 分為最低分，請將分數依照 index 列出來
            [
               {
                 "idea": "技能目錄分類",
                 "tag": "Category",
                 "advantage": "方便搜尋技能",
                 "disadvantage": "分類可能不完整"
               },
               {
                 "idea": "使用者技能等級",
                 "tag": "Level",
                 "advantage": "匹配適合的對象",
                 "disadvantage": "難以客觀評估"
               },
               {
                 "idea": "學習進度追蹤",
                 "tag": "Progress",
                 "advantage": "掌握學習狀況",
                 "disadvantage": "可能造成壓力"
               },
               {
                 "idea": "技能交換媒合",
                 "tag": "Match",
                 "advantage": "提高學習效率",
                 "disadvantage": "匹配不準確"
               },
               {
                 "idea": "線上討論社群",
                 "tag": "Community",
                 "advantage": "互相交流經驗",
                 "disadvantage": "可能偏離主題"
               },
               {
                   "idea": "技能樹圖表",
                   "tag": "Visual",
                   "advantage": "清楚易懂",
                   "disadvantage": "可能過於複雜"
                 },
                 {
                   "idea": "直播教學功能",
                   "tag": "Live",
                   "advantage": "互動性高",
                   "disadvantage": "需要穩定網路"
                 },
                 {
                   "idea": "社群討論區",
                   "tag": "Forum",
                   "advantage": "交流互動",
                   "disadvantage": "可能缺乏效率"
                 },
                 {
                   "idea": "資源分享平台",
                   "tag": "Resource",
                   "advantage": "資源豐富",
                   "disadvantage": "品質參差不齊"
                 },
                 {
                   "idea": "線上測驗評估",
                   "tag": "Test",
                   "advantage": "學習效果評估",
                   "disadvantage": "可能過於死板"
                 }
            ]
            """.trimIndent()

            val res = model.generateContent(prompt2)

            Timber.d("sam00 text2=${res.text}")
        }
    }
}
