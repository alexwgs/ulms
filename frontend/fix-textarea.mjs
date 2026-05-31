// Fix type="textarea" → <t-textarea> and v-loading → :loading on t-table
import { readFileSync, writeFileSync } from 'fs'
import { readdirSync } from 'fs'
import { join, extname } from 'path'

function walkDir(dir, files = []) {
  for (const entry of readdirSync(dir, { withFileTypes: true })) {
    const full = join(dir, entry.name)
    if (entry.isDirectory()) walkDir(full, files)
    else if (['.vue', '.js'].includes(extname(entry.name))) files.push(full)
  }
  return files
}

const files = walkDir('src')
let totalFixes = 0

for (const file of files) {
  const original = readFileSync(file, 'utf-8')
  let content = original
  let changed = false

  // 1. Fix <t-input type="textarea" → <t-textarea
  // Handle all attribute orderings
  content = content.replace(
    /<t-input\b([^>]*)\btype="textarea"([^>]*)>/g,
    (match, before, after) => {
      changed = true
      // Clean up element-loading attributes that were on textarea inputs
      const cleaned = (before + after)
        .replace(/\s+element-loading-[a-z-]+="[^"]*"/g, '')
      return `<t-textarea${cleaned}>`
    }
  )
  // Fix closing tags
  content = content.replace(/<\/t-input>/g, (match) => {
    // Only replace if we changed an opening t-input to t-textarea in this pass
    // This is a bit aggressive — let's check if there was already a change
    return changed ? match : match
  })

  // Actually, the closing tag fix is tricky. Let me just use a more specific approach.
  // Re-read to reset
  content = readFileSync(file, 'utf-8')
  changed = false

  // Fix type="textarea" on t-input
  content = content.replace(
    /<t-input\b([^>]*)\btype="textarea"([^>]*?)(\/?)>/g,
    (match, before, after, selfClose) => {
      changed = true
      const cleaned = (before + after)
        .replace(/\s+element-loading-[a-z-]+="[^"]*"/g, '')
        .trim()
      return `<t-textarea${cleaned ? ' ' + cleaned : ''}>`
    }
  )

  // Fix corresponding </t-input> that was actually a textarea
  // We'll use a state machine: track if we're inside a textarea that was converted
  // For simplicity, just do a separate pass on known files
  content = content.replace(
    /([\s\S]*?<t-textarea\b[^>]*>[\s\S]*?)<\/t-input>/g,
    '$1</t-textarea>'
  )

  // 2. Fix v-loading on t-table → :loading prop
  content = content.replace(
    /<t-table\b([^>]*)\bv-loading="([^"]+)"([^>]*)/g,
    '<t-table$1:loading="$2"$3'
  )

  // 3. Remove element-loading-* attributes
  content = content.replace(
    /\s+element-loading-[a-z-]+="[^"]*"/g,
    ''
  )

  if (content !== original) {
    writeFileSync(file, content, 'utf-8')
    totalFixes++
    console.log(`Fixed: ${file}`)
  }
}

console.log(`\nTotal files fixed: ${totalFixes}`)
