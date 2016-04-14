/*
 * Copyright (C) 2016 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.sqldelight.intellij.lang

import com.intellij.codeInsight.completion.*
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.ElementPatternCondition
import com.intellij.patterns.InitialPatternCondition
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext

class SqlDelightCompletionContributor : CompletionContributor() {
  init {
    println("AIGHT STARTIN DIS")
    extend(
        CompletionType.BASIC,
        SqlDelightPattern(),
        SqlDelightCompletionProvider()
    )
  }
}

class SqlDelightCompletionProvider : CompletionProvider<CompletionParameters>() {
  override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext?,
      result: CompletionResultSet) {
    println("SUPSUPSUPSUP at offset ${parameters.offset}")
    (parameters.originalFile as SqliteFile).parseThen({ parsed ->
      parsed.sql_stmt_list().sql_stmt().forEach { sql_stmt ->
        println("Try this element ${sql_stmt.text} at ${sql_stmt.start.startIndex}")
        if (sql_stmt.start.startIndex < parameters.offset && sql_stmt.stop.stopIndex < parameters.offset) {
          println("FOUND THE ELEMENT ${sql_stmt.text}")
        }
      }
    })
  }
}

class SqlDelightPattern : ElementPattern<PsiElement> {
  override fun accepts(o: Any?, context: ProcessingContext?): Boolean {
    return true
  }

  override fun getCondition(): ElementPatternCondition<PsiElement>? {
    return ElementPatternCondition(object : InitialPatternCondition<PsiElement>(PsiElement::class.java) {

    })
  }

  override fun accepts(o: Any?): Boolean {
    return true
  }
}
