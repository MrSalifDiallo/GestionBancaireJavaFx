# Vérifier si pandoc est installé
$pandocPath = Get-Command pandoc -ErrorAction SilentlyContinue

if (-not $pandocPath) {
    Write-Host "Pandoc n'est pas installé. Installation via winget..."
    winget install pandoc
}

# Convertir le fichier Markdown en PDF
pandoc documentation.md -o documentation.pdf --pdf-engine=wkhtmltopdf --css=pdf_styles.css 