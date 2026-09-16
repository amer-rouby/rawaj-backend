var r=`#4338ca`;var a=`#1e293b`;var i=`#64748b`;var s=`#cbd5e1`;var l=`#f1f5f9`;function p(t){return t?`'Tahoma', 'Segoe UI', Arial, sans-serif`:`'Segoe UI', Roboto, Arial, sans-serif`}function g(t){let n=t?`rtl`:`ltr`,o=t?`right`:`left`;return`
    * { margin: 0; padding: 0; box-sizing: border-box; }
    html, body { font-family: ${p(t)}; color: ${a}; background: #fff; }
    body { direction: ${n}; padding: 12mm 10mm; font-size: 12px; line-height: 1.5; }
    .print-doc { max-width: 190mm; margin: 0 auto; }

    .letterhead { text-align: center; padding-bottom: 10px; margin-bottom: 18px; border-bottom: 2px solid ${r}; }
    .ph-name { font-size: 20px; font-weight: 700; color: ${a}; }
    .ph-meta { font-size: 11px; color: ${i}; margin-top: 4px; }
    .doc-title { font-size: 16px; font-weight: 700; color: ${r}; margin-top: 10px; }
    .doc-subtitle { font-size: 12px; color: ${i}; margin-top: 2px; }
    .doc-meta { font-size: 10px; color: ${i}; margin-top: 4px; }

    table { width: 100%; border-collapse: collapse; page-break-inside: auto; }
    thead { display: table-header-group; }
    thead th {
      background: ${l}; color: ${a}; font-weight: 700; font-size: 11px;
      text-align: ${o}; padding: 8px; border-bottom: 2px solid ${r};
    }
    tbody tr { page-break-inside: avoid; }
    tbody td { padding: 7px 8px; border-bottom: 1px solid ${s}; text-align: ${o}; font-size: 11.5px; }
    tbody tr:nth-child(even) { background: #fafafa; }

    .doc-footer {
      text-align: center; margin-top: 22px; padding-top: 10px; border-top: 1px solid ${s};
      color: ${i}; font-size: 10px;
    }

    @page { size: A4; margin: 0; }
    @media print {
      body { padding: 12mm 10mm; }
      a { color: inherit; text-decoration: none; }
    }
  `}function c(t,n,o,e){let d=[t.address,t.phone,t.email].filter(Boolean);return`
    <header class="letterhead">
      <div class="ph-name">${t.name}</div>
      ${d.length?`<div class="ph-meta">${d.join(`&nbsp;&middot;&nbsp;`)}</div>`:``}
      <div class="doc-title">${n}</div>
      ${o?`<div class="doc-subtitle">${o}</div>`:``}
      ${e?`<div class="doc-meta">${e}</div>`:``}
    </header>
  `}function m(t,n,o){return`<footer class="doc-footer">${t}&nbsp;&mdash;&nbsp;${n}: ${new Date().toLocaleString(o?`ar-EG-u-nu-latn`:`en-US`,{year:`numeric`,month:`long`,day:`numeric`,hour:`2-digit`,minute:`2-digit`})}</footer>`}export{g as n,m as r,c as t};