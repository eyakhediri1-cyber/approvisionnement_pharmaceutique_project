import os
import json

def clean_file(path):
    try:
        with open(path, "r", encoding="utf-8") as f:
            content = f.read()
    except Exception as e:
        print(f"Skipping {path}: {e}")
        return
    
    original = content
    content_stripped = content.strip()
    
    # 1. Try JSON decoding if it starts and ends with double quotes
    if content_stripped.startswith('"') and content_stripped.endswith('"'):
        try:
            content = json.loads(content_stripped)
        except Exception:
            # Fallback manual
            content = content_stripped[1:-1]
            content = content.replace('\\\\', '\\').replace('\\n', '\n').replace('\\t', '\t').replace('\\"', '"')
    else:
        # 2. Or replace manual escapes if any
        if '\\n' in content or '\\t' in content or '\\"' in content:
            content = content.replace('\\n', '\n').replace('\\t', '\t').replace('\\"', '"').replace('\\\\', '\\')
            if content.startswith('"') and content.endswith('"'):
                content = content[1:-1]
                
    if content != original:
        with open(path, "w", encoding="utf-8") as f:
            f.write(content)
        print(f"Cleaned: {path}")

# Walk both directories
paths = [
    "/home/eya/Eya2eme/projet_stage/backend-spring",
    "/home/eya/Eya2eme/projet_stage/pharmacie"
]

for root_dir in paths:
    for dirpath, _, filenames in os.walk(root_dir):
        if "node_modules" in dirpath or ".angular" in dirpath or "dist" in dirpath:
            continue
        for filename in filenames:
            if filename.endswith((".java", ".ts", ".html", ".css", ".properties", ".sql", ".md", ".json")):
                clean_file(os.path.join(dirpath, filename))

print("All files cleaned successfully!")
