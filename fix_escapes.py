import os
import json

paths = [
    "/home/eya/Eya2eme/projet_stage/backend-spring",
    "/home/eya/Eya2eme/projet_stage/pharmacie/src/app"
]

def clean_file(path):
    try:
        with open(path, "r", encoding="utf-8") as f:
            content = f.read()
    except Exception as e:
        print(f"Skipping {path}: {e}")
        return
    
    original = content
    # If the content is wrapped in quotes, it is a JSON string
    if content.strip().startswith('"') and content.strip().endswith('"'):
        try:
            content = json.loads(content.strip())
        except:
            # Fallback manual unescape
            content = content.strip()[1:-1]
            content = content.replace('\\n', '\n').replace('\\t', '\t').replace('\\"', '"').replace('\\\\', '\\')
    else:
        # Check if it has escaped newlines without wrapping quotes
        if '\\n' in content and ('package ' in content or 'import ' in content or 'public ' in content or 'selector' in content):
            content = content.replace('\\n', '\n').replace('\\t', '\t').replace('\\"', '"').replace('\\\\', '\\')
            if content.startswith('"') and content.endswith('"'):
                content = content[1:-1]
    
    if content != original:
        with open(path, "w", encoding="utf-8") as f:
            f.write(content)
        print(f"Cleaned: {path}")

for root_dir in paths:
    for dirpath, _, filenames in os.walk(root_dir):
        # Skip node_modules just in case
        if "node_modules" in dirpath:
            continue
        for filename in filenames:
            if filename.endswith((".java", ".ts", ".html", ".css", ".properties", ".sql", ".md")):
                clean_file(os.path.join(dirpath, filename))

print("Unescaping completed successfully.")
