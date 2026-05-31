const iconMap = {
  // 导航/系统
  HomeIcon: 'home',
  Home: 'home',
  Setting: 'setting',
  Setting2: 'setting',
  Setting3: 'setting',
  User: 'user',
  UserIcon: 'user',
  UserCircleIcon: 'user-circle',
  Grid: 'view-module',
  Menu: 'menu',
  MenuIcon: 'menu',
  Management: 'user-setting',

  // 通知/状态
  NotificationIcon: 'notification',
  MoonIcon: 'moon',
  SunnyIcon: 'sunny',
  SwapIcon: 'swap',

  // 操作
  Edit: 'edit',
  Delete: 'delete',
  Search: 'search',
  AddIcon: 'add',
  RemoveIcon: 'minus',
  Close: 'close',
  Check: 'check',
  CheckCircleIcon: 'check-circle',
  CheckCircleFilledIcon: 'check-circle-filled',
  CloseCircleIcon: 'close-circle',
  ErrorCircleIcon: 'error-circle',
  ErrorCircleFilledIcon: 'error-circle-filled',
  InfoFilled: 'info-circle-filled',
  HelpCircleFilledIcon: 'help-circle-filled',

  // 方向
  ArrowDown: 'chevron-down',
  ArrowLeft: 'chevron-left',
  ArrowRight: 'chevron-right',
  ArrowUp: 'chevron-up',
  DArrowLeft: 'chevron-left-double',
  DArrowRight: 'chevron-right-double',
  Top: 'arrow-up',
  Bottom: 'arrow-down',
  Back: 'rollback',

  // 文件/数据
  Upload: 'upload',
  Download: 'download',
  Refresh: 'refresh',
  RefreshRight: 'refresh',
  RefreshLeft: 'refresh',
  Share: 'share',
  Star: 'star',
  StarFilled: 'star-filled',
  StarOn: 'star-filled',
  ImageIcon: 'image',
  PictureFilled: 'image',
  Document: 'file',
  FileIcon: 'file',
  Folder: 'folder',
  FolderOpened: 'folder-open',
  Link: 'link',
  CopyDocument: 'file-copy',
  Printer: 'print',

  // 位置/安全
  Location: 'location',
  LocationIcon: 'location',
  MapLocation: 'location',
  Lock: 'lock-on',
  Unlock: 'lock-off',
  Key: 'secured',

  // 通信
  Phone: 'call',
  PhoneFilled: 'call',
  ChatIcon: 'chat',
  ChatDotRound: 'chat-bubble',
  ChatDotSquare: 'chat-bubble',
  ChatLineSquare: 'chat-bubble',
  ChatRound: 'chat-bubble',
  Postcard: 'mail',

  // 视图
  View: 'browse',
  Hide: 'browse-off',
  Open: 'browse',
  Preview: 'browse',
  Browse: 'browse',

  // 商业
  PriceTag: 'discount',
  TicketIcon: 'ticket',
  Tickets: 'ticket',
  Goods: 'shop',
  Sell: 'money',
  ShoppingCart: 'cart',
  ShoppingCartFull: 'cart',
  Shop: 'shop',
  Present: 'gift',

  // 图表/数据
  DataAnalysis: 'chart',
  DataBoard: 'dashboard',
  Histogram: 'chart-bar',
  PieChart: 'chart-pie',
  TrendCharts: 'chart-line',
  ChartLineIcon: 'chart-line',

  // 时间
  TimeIcon: 'time',
  AlarmClock: 'alarm',
  Calendar: 'calendar',
  Date: 'calendar',

  // 列表/排序
  List: 'view-list',
  TList: 'view-agenda',
  Expand: 'unfold-more',
  Fold: 'unfold-less',
  Operation: 'gesture-click',
  Filter: 'filter',
  Rank: 'order-descending',
  Sort: 'swap',
  SortUp: 'order-ascending',
  SortDown: 'order-descending',
  Connection: 'link',

  // 媒体
  VideoCamera: 'video',
  VideoCameraFilled: 'video',
  VideoPlay: 'play-circle',
  VideoPause: 'pause-circle',
  Headset: 'earphone',
  Microphone: 'microphone',
  TurnOffMicrophone: 'microphone',
  Film: 'play-circle',

  // 设备
  Monitor: 'desktop',
  Iphone: 'mobile',
  Cellphone: 'mobile',
  Cpu: 'cpu',

  // 工具
  Guide: 'compass',
  Compass: 'compass',
  TargetIcon: 'location',
  BooksIcon: 'books',
  BookOpenIcon: 'book-open',
  MagicStick: 'highlight',
  Brush: 'format-color',
  Scissor: 'cut',
  Crop: 'cut',
  ScaleToOriginal: 'fullscreen',
  FullScreen: 'fullscreen',
  More: 'ellipsis',
  MoreFilled: 'ellipsis',
  Tools: 'tools',
  TurnOff: 'poweroff',

  // 建筑/教育
  OfficeBuilding: 'building',
  School: 'education',
  Education: 'education',

  // 收藏
  BookmarkIcon: 'bookmark',
  CollectionTag: 'bookmark',
}

/**
 * Convert PascalCase string to kebab-case (e.g., "UserCircleIcon" → "user-circle")
 * Handles "Filled" suffix → "-filled"
 */
function pascalToKebab(str) {
  return str
    .replace(/Icon$/, '')
    .replace(/Filled$/, '-filled')
    .replace(/([A-Z])/g, '-$1')
    .toLowerCase()
    .replace(/^-/, '')
    .replace(/--/g, '-')
}

/**
 * Map an icon name (typically from backend menu data) to a valid TDesign icon stem.
 * Falls back to converting PascalCase → kebab-case for names not in the explicit map.
 */
export function mapIcon(elIconName) {
  if (!elIconName) return 'app'
  if (iconMap[elIconName]) return iconMap[elIconName]
  return pascalToKebab(elIconName)
}

export default iconMap
