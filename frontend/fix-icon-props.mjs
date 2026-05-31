import { readFileSync, writeFileSync } from 'fs';

// Files to process (excluding iconfont ones and special cases)
const files = [
  'src/views/font/welcome/index.vue',
  'src/views/font/welcome/components/todolist.vue',
  'src/views/font/welcome/components/DailyTask.vue',
  'src/views/font/edu/MyQuestion.vue',
  'src/views/font/edu/component/ExamBook.vue',
  'src/views/font/edu/Brush.vue',
  'src/views/exception/not-found/index.vue',
  'src/views/admin/system/role/index.vue',
  'src/views/admin/system/menu/index.vue',
  'src/views/admin/college/config/components/EvaluateTempleteConfig.vue',
  'src/views/admin/college/config/components/CourseTypeConfig.vue',
  'src/views/font/college/course/study.vue',
  'src/views/font/college/exam/index.vue',
  'src/views/font/a6voice/index.vue',
  'src/views/font/a6voice/my-item/index.vue',
  'src/views/admin/db/config/index.vue',
  'src/views/admin/oht/todolist/manage/index.vue',
  'src/views/admin/it_op/userFiles/index.vue',
  'src/views/admin/helper/route/index.vue',
  'src/views/admin/oht/status/view/index.vue',
  'src/views/admin/it_op/notice/index.vue',
  'src/views/admin/edu/question/index.vue',
  'src/views/admin/oht/status/detail/index.vue',
  'src/views/admin/oht/status/config/index.vue',
  'src/views/admin/edu/question/components/QuestionDialog.vue',
  'src/views/admin/edu/daily_config/index.vue',
  'src/views/admin/edu/exam_config/index.vue',
  'src/views/admin/edu/daily_config/components/QuesSelectDialog.vue',
  'src/views/admin/edu/exam_config/components/BookInfo.vue',
  'src/views/admin/edu/exam_config/components/ExamBook.vue',
  'src/views/admin/edu/exam_config/components/ExamQuestionConfig.vue',
  'src/views/admin/edu/exam_config/components/ExamScore.vue',
  'src/views/admin/edu/exam_config/components/ExamTimeConfig.vue',
  'src/views/admin/it_op/onlineMonitor/index.vue',
  'src/views/admin/edu/exam_config/components/ExamScore.vue',
];

for (const file of files) {
  let content;
  try {
    content = readFileSync(file, 'utf-8');
  } catch { continue; }
  
  let modified = false;
  
  // Pattern 1: Self-closing <t-button ... icon="xxx" ... />  (no children)
  // → <t-button ...><template #icon><DynamicIcon name="xxx" /></template></t-button>
  const selfCloseRegex = /(<t-(button|step|link)\b[^>]*?)\s+icon="([a-z][a-z0-9-]*)"(\s*\/?>)/g;
  content = content.replace(selfCloseRegex, (match, before, tag, iconName, close) => {
    // Only replace if it's self-closing (/>) or has no children after >
    if (close.includes('/')) {
      modified = true;
      return `${before}><template #icon><DynamicIcon name="${iconName}" /></template></${tag}>`;
    }
    return match;
  });
  
  // Pattern 2: <t-button ... icon="xxx" ...>text</t-button> (has children)
  const withChildrenRegex = /(<t-(button|step|link)\b[^>]*?)\s+icon="([a-z][a-z0-9-]*)"(\s*)([^>]*>)/g;
  content = content.replace(withChildrenRegex, (match, before, tag, iconName, space, after) => {
    modified = true;
    return `${before}${after}<template #icon><DynamicIcon name="${iconName}" /></template>`;
  });
  
  if (modified) {
    writeFileSync(file, content, 'utf-8');
    console.log('Fixed:', file);
  }
}

console.log('Done');
