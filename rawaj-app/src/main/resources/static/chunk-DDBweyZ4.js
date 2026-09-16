import{Jn as c,Ri as u_,pi as pe}from"./chunk-BiI_Tx0v.js";import{c as wi}from"./chunk-B7E2GVbx.js";import{i as o}from"./main-ROQENEH7.js";import{n as g,r as m,t as c$1}from"./chunk-CkM58mCf.js";import{t as f}from"./chunk-MSopXb_T.js";var S=class c$2{translate=c(u_);languageService=c(o);currencyService=c(f);authService=c(wi);printReport(t){let e=this.languageService.getCurrentLanguage()===`ar`,n=t.direction||(e?`rtl`:`ltr`),l=e?`ar`:`en`,s=o=>{let r=this.translate.instant(o);return r&&r!==o?r:o},d=this.generatePrintHtml(t,s,e,n,l),a=document.createElement(`iframe`);a.style.display=`none`,a.src=`about:blank`,document.body.appendChild(a);let i=a.contentDocument||a.contentWindow?.document;i&&(i.open(),i.write(d),i.close(),a.onload=()=>{setTimeout(()=>{a.contentWindow?.print(),setTimeout(()=>{document.body.removeChild(a)},100)},300)})}generatePrintHtml(t,e,n,l,s){let d=t.summary?.length?`
      <div class="summary-section">
        <div class="summary-grid">
          ${t.summary.map(r=>`
            <div class="summary-item">
              <div class="summary-label">${r.label}</div>
              <div class="summary-value">${this.formatValue(r.value,r.type||`text`,n)}</div>
            </div>
          `).join(``)}
        </div>
      </div>
    `:``,a=t.data.length>0?`
      <table class="data-table">
        <thead>
          <tr>
            ${t.columns.map(r=>`
              <th style="${r.width?`width: ${r.width}`:``}">${r.label}</th>
            `).join(``)}
          </tr>
        </thead>
        <tbody>
          ${t.data.map(r=>`
            <tr>
              ${t.columns.map(u=>`
                <td>${this.formatValue(r[u.key],u.type||`text`,n)}</td>
              `).join(``)}
            </tr>
          `).join(``)}
        </tbody>
      </table>
    `:`
      <div class="no-data">
        <p>${e(`REPORTS.NO_DATA`)||`No data available`}</p>
      </div>
    `,i=this.authService.getStoreInfo()?.name||e(`APP.NAME`),o=`${e(`REPORTS.GENERATED`)||`Generated`}: ${new Date().toLocaleDateString(s===`ar`?`ar-EG`:`en-US`,{year:`numeric`,month:`long`,day:`numeric`,hour:`2-digit`,minute:`2-digit`})}`;return`<!DOCTYPE html>
<html dir="${l}" lang="${s}">
<head>
  <meta charset="UTF-8">
  <title>${t.title}</title>
  <style>
    ${g(n)}
    .summary-section { margin-bottom: 20px; }
    .summary-grid { display: flex; flex-wrap: wrap; gap: 12px; justify-content: center; }
    .summary-item {
      background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 6px;
      padding: 10px 16px; min-width: 130px; text-align: center;
    }
    .summary-label { font-size: 10px; color: #64748b; margin-bottom: 4px; }
    .summary-value { font-size: 15px; font-weight: 700; color: #1e293b; }
    .no-data { text-align: center; padding: 40px; color: #94a3b8; font-size: 13px; }
  </style>
</head>
<body>
  <div class="print-doc">
    ${c$1({name:i},t.title,t.subtitle,o)}
    ${d}
    ${a}
    ${m(e(`APP.NAME`),e(`REPORTS.GENERATED`)||`Generated`,n)}
  </div>
</body>
</html>`}formatValue(t,e,n){if(t==null)return`-`;switch(e){case`currency`:return new Intl.NumberFormat(n?`ar-EG`:`en-US`,{style:`currency`,currency:this.currencyService.getCode(),minimumFractionDigits:2}).format(t);case`number`:return new Intl.NumberFormat(n?`ar-EG`:`en-US`).format(t);case`date`:try{return new Date(t).toLocaleDateString(n?`ar-EG`:`en-US`,{year:`numeric`,month:`short`,day:`numeric`})}catch(l){return t}default:return t}}static ɵfac=function(e){return new(e||c$2)};static ɵprov=pe({token:c$2,factory:c$2.ɵfac,providedIn:`root`})};export{S as t};