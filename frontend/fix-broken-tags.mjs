import { readFileSync, writeFileSync } from 'fs';

const vueFiles = process.argv.slice(2);
if (vueFiles.length === 0) {
  // Find all broken files
  const { execSync } = await import('child_process');
  const result = execSync('grep -rl "t-buttoncircle\|t-button@\|t-buttonsize\|t-buttontheme\|t-buttonicon\|t-step@\|t-link@" src/views src/layouts src/components --include="*.vue" 2>/dev/null || true', { encoding: 'utf-8' });
  vueFiles.push(...result.trim().split('\n').filter(Boolean));
}

for (const file of vueFiles) {
  let content = readFileSync(file, 'utf-8');
  let modified = false;

  // Fix 1: Concatenated attribute names for t-button
  // t-button@ → t-button @, t-buttoncircle → t-button circle, etc.
  const concatFixes = [
    [/<t-button@/g, '<t-button @'],
    [/<t-buttoncircle\b/g, '<t-button circle'],
    [/<t-buttonsize=/g, '<t-button size='],
    [/<t-buttontheme=/g, '<t-button theme='],
    [/<t-buttonicon=/g, '<t-button icon='],
    [/<t-buttonclass=/g, '<t-button class='],
    [/<t-buttonstyle=/g, '<t-button style='],
    [/<t-buttonv-/g, '<t-button v-'],
    [/<t-button:(\w)/g, '<t-button :$1'],
    [/<t-buttonvariant=/g, '<t-button variant='],
    [/<t-buttonplain\b/g, '<t-button plain'],
    [/<t-buttonround\b/g, '<t-button round'],
    [/<t-buttondisabled\b/g, '<t-button disabled'],
    [/<t-buttonloading\b/g, '<t-button loading'],
    
    [/<t-step@/g, '<t-step @'],
    [/<t-stepv-/g, '<t-step v-'],
    
    [/<t-link@/g, '<t-link @'],
    [/<t-linkv-/g, '<t-link v-'],
    [/<t-link:(\w)/g, '<t-link :$1'],
  ];

  for (const [rx, replacement] of concatFixes) {
    if (rx.test(content)) {
      content = content.replace(rx, replacement);
      modified = true;
    }
  }

  // Fix 2: Self-closing tags that now have template outside
  // Pattern: /><template #icon>... → ><template #icon>...</t-button>
  // This happens when a self-closing t-button had icon="xxx" and got split
  const selfCloseFix = /(\s*)\/>(\s*)<template #icon><DynamicIcon name="([^"]+)" \/><\/template>/g;
  if (selfCloseFix.test(content)) {
    content = content.replace(selfCloseFix, (match, sp1, sp2, iconName) => {
      return `><template #icon><DynamicIcon name="${iconName}" /></template></t-button>`;
    });
    modified = true;
  }

  // Fix 3: Similar for t-step and t-link self-closing
  const selfCloseStepFix = /(\s*)\/>(\s*)<template #icon><DynamicIcon name="([^"]+)" \/><\/template>\s*<\/t-step>/g;
  content = content.replace(selfCloseStepFix, (match, sp1, sp2, iconName) => {
    modified = true;
    return `><template #icon><DynamicIcon name="${iconName}" /></template></t-step>`;
  });

  const selfCloseLinkFix = /(\s*)\/>(\s*)<template #icon><DynamicIcon name="([^"]+)" \/><\/template>\s*<\/t-link>/g;
  content = content.replace(selfCloseLinkFix, (match, sp1, sp2, iconName) => {
    modified = true;
    return `><template #icon><DynamicIcon name="${iconName}" /></template></t-link>`;
  });

  if (modified) {
    writeFileSync(file, content, 'utf-8');
    console.log('Fixed:', file);
  }
}
console.log('Done');
